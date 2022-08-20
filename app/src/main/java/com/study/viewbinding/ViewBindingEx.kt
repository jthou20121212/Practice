package com.study.viewbinding

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private const val TAG: String = "ViewBinding"

// 不需要调用 setContentView()
fun <VB : ViewBinding> ComponentActivity.viewBinding(viewBinder: (LayoutInflater) -> VB): ReadOnlyProperty<ComponentActivity, VB> = ActivityViewBindingProperty {
    viewBinder(layoutInflater).apply { setContentView(root) }
}

// 需要使用 Activity 中调用 setContentView()
fun <VB : ViewBinding> ComponentActivity.viewBinding2(viewBinder: (LayoutInflater, ViewGroup, Boolean) -> VB): ReadOnlyProperty<ComponentActivity, VB> = ActivityViewBindingProperty {
    viewBinder(layoutInflater, requireViewByIdCompat(android.R.id.content), true)
}

// 不需要调用 setContentView()
fun <VB : ViewBinding> ComponentActivity.viewBinding3(viewBinder: (View) -> VB, @LayoutRes layoutRes: Int): ReadOnlyProperty<ComponentActivity, VB> = ActivityViewBindingProperty {
    viewBinder(layoutInflater.inflate(layoutRes, null)).apply { setContentView(root) }
}

class ActivityViewBindingProperty<in A : ComponentActivity, out V : ViewBinding>(viewBinder: (A) -> V): LifecycleViewBindingProperty<A, V>(viewBinder) {
    override fun getLifecycleOwner(thisRef: A): LifecycleOwner {
        return thisRef
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <F: Fragment, VB : ViewBinding> Fragment.viewBinding(
    crossinline viewBinder: (View) -> VB,
    crossinline viewProvider: (F) -> View = Fragment::requireView
): ViewBindingProperty<F, VB> = when(this){
    is DialogFragment -> DialogFragmentViewBindingProperty { fragment: F ->
        viewBinder(viewProvider(fragment))
    } as ViewBindingProperty<F, VB>
    else -> FragmentViewBindingProperty { fragment: F ->
        viewBinder(viewProvider(fragment))
    }
}

class DialogFragmentViewBindingProperty<in F : DialogFragment, out V : ViewBinding>(
    viewBinder: (F) -> V
) : LifecycleViewBindingProperty<F, V>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        return if (thisRef.view == null) {
            thisRef
        } else {
            try {
                thisRef.viewLifecycleOwner
            } catch (ignored: IllegalStateException) {
                error("Fragment doesn't have view associated with it or the view has been destroyed")
            }
        }
    }
}

class FragmentViewBindingProperty<in F : Fragment, out V : ViewBinding>(
    viewBinder: (F) -> V
) : LifecycleViewBindingProperty<F, V>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        try {
            return thisRef.viewLifecycleOwner
        } catch (ignored: IllegalStateException) {
            error("Fragment doesn't have view associated with it or the view has been destroyed")
        }
    }
}

interface ViewBindingProperty<in T : Any, out V : ViewBinding> : ReadOnlyProperty<T, V> {
    @MainThread
    fun clear()
}

abstract class LifecycleViewBindingProperty<in T: Any, out V: ViewBinding> (
    private val viewBinder: (T) -> V
        ) : ViewBindingProperty<T, V> {

    private var viewBindingGlobal: V? = null

    protected abstract fun getLifecycleOwner(thisRef: T): LifecycleOwner

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        viewBindingGlobal?.let { return it }

        val lifecycle = getLifecycleOwner(thisRef).lifecycle
        val viewBindingLocal = viewBinder(thisRef)

        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            Log.w(
                TAG, "Access to viewBinding after Lifecycle is destroyed or hasn't created yet. " +
                        "The instance of viewBinding will be not cached."
            )
            // We can access to ViewBinding after Fragment.onDestroyView(), but don't save it to prevent memory leak
        } else {
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver(this))
            viewBindingGlobal = viewBindingLocal
        }
        return viewBindingLocal
    }

    override fun clear() {
        viewBindingGlobal = null
    }

    private class ClearOnDestroyLifecycleObserver(
        private val property: LifecycleViewBindingProperty<*, *>
    ) : DefaultLifecycleObserver {

        private companion object {
            private val mainHandler = Handler(Looper.getMainLooper())
        }

        override fun onDestroy(owner: LifecycleOwner) {
            mainHandler.post { property.clear() }
        }

    }

}

/**
 * Utility to find root view for ViewBinding in Activity
 */
fun findRootView(activity: Activity): View {
    val contentView = activity.findViewById<ViewGroup>(android.R.id.content)
    checkNotNull(contentView) { "Activity has no content view" }
    return when (contentView.childCount) {
        1 -> contentView.getChildAt(0)
        0 -> error("Content view has no children. Provide root view explicitly")
        else -> error("More than one child view found in Activity content view")
    }
}

fun DialogFragment.getRootView(viewBindingRootId: Int): View {
    val dialog = checkNotNull(dialog) {
        "DialogFragment doesn't have dialog. Use viewBinding delegate after onCreateDialog"
    }
    val window = checkNotNull(dialog.window) { "Fragment's Dialog has no window" }
    return with(window.decorView) {
        if (viewBindingRootId != 0) requireViewByIdCompat(
            viewBindingRootId
        ) else this
    }
}

fun <V : View> View.requireViewByIdCompat(@IdRes id: Int): V {
    return ViewCompat.requireViewById(this, id)
}

fun <V : View> Activity.requireViewByIdCompat(@IdRes id: Int): V {
    return ActivityCompat.requireViewById(this, id)
}

