//package com.study.viewmodel
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.viewModels
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//
//class MainRepository
//
//class MainViewModel(val repository: MainRepository) : ViewModel()
//
//class MyViewModelFactory constructor(private val repository: MainRepository) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
//
//class MainActivity : ComponentActivity() {
//
//    private val viewModel: MainViewModel by viewModels {
//        viewModelFactory
//    }
//    private val repository: MainRepository = MainRepository()
//    private val viewModelFactory: MyViewModelFactory = MyViewModelFactory(repository)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // use viewModel
//    }
//}
