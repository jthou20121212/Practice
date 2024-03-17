.class public Lcom/jthou/jvm/Target;
.super Ljava/lang/Object;
.source "Target.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private testAsm()V
    .registers 1
    .annotation build Lcom/jthou/jvm/InjectHello;
    .end annotation

    .prologue
    .line 6
    return-void
.end method
