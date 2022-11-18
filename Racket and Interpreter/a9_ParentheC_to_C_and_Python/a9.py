import sys
from enum import Enum
from greenlet import greenlet
from typing import Any, Callable

# Define the program counter
g_pc : Callable[[], Any] = None

# Define the registers
g__exp_: object = None
g__env_: object = None
g__k_: object = None
g__rator_: object = None
g__rr2_: object = None
g__v_: object = None
g__vari_: object = None

# Define the dismount greenlet
_dismount_blank = None

# Define the union classes
class union_t(object):
    class expr(Enum):
        const = 0
        var = 1
        _if = 2
        mult = 3
        subr1 = 4
        zero = 5
        letcc = 6
        throw = 7
        let = 8
        _lambda = 9
        app = 10

    class clos(Enum):
        closure = 0

    class envr(Enum):
        empty = 0
        extend_env = 1

    class kt(Enum):
        empty_k = 0
        subr1_k = 1
        zero_k = 2
        mult_outer_k = 3
        mult_inner_k = 4
        if_k = 5
        let_k = 6
        throw_outer_k = 7
        throw_inner_k = 8
        app_outer_k = 9
        app_inner_k = 10

    def __init__(self, type: Enum, **kwargs):
        self.type = type
        for key in kwargs:
            setattr(self, key, kwargs[key])


# Union functions
def kt_empty_k(dismount):
    return union_t(union_t.kt.empty_k, 
            dismount=dismount)

def kt_subr1_k(k):
    return union_t(union_t.kt.subr1_k, 
            k=k)

def kt_zero_k(k):
    return union_t(union_t.kt.zero_k, 
            k=k)

def kt_mult_outer_k(env_cps, x, k):
    return union_t(union_t.kt.mult_outer_k, 
            env_cps=env_cps, 
            x=x, 
            k=k)

def kt_mult_inner_k(v, k):
    return union_t(union_t.kt.mult_inner_k, 
            v=v, 
            k=k)

def kt_if_k(env_cps, conseq, alt, k):
    return union_t(union_t.kt.if_k, 
            env_cps=env_cps, 
            conseq=conseq, 
            alt=alt, 
            k=k)

def kt_let_k(env_cps, body, k):
    return union_t(union_t.kt.let_k, 
            env_cps=env_cps, 
            body=body, 
            k=k)

def kt_throw_outer_k(env_cps, k_exp):
    return union_t(union_t.kt.throw_outer_k, 
            env_cps=env_cps, 
            k_exp=k_exp)

def kt_throw_inner_k(v, env_cps):
    return union_t(union_t.kt.throw_inner_k, 
            v=v, 
            env_cps=env_cps)

def kt_app_outer_k(env_cps, rr2, k):
    return union_t(union_t.kt.app_outer_k, 
            env_cps=env_cps, 
            rr2=rr2, 
            k=k)

def kt_app_inner_k(v, k):
    return union_t(union_t.kt.app_inner_k, 
            v=v, 
            k=k)

def envr_empty():
    return union_t(union_t.envr.empty, )

def envr_extend_env(v, env):
    return union_t(union_t.envr.extend_env, 
            v=v, 
            env=env)

def clos_closure(body, env_cps):
    return union_t(union_t.clos.closure, 
            body=body, 
            env_cps=env_cps)

def expr_const(cexp):
    return union_t(union_t.expr.const, 
            cexp=cexp)

def expr_var(n):
    return union_t(union_t.expr.var, 
            n=n)

def expr_if(test, conseq, alt):
    return union_t(union_t.expr._if, 
            test=test, 
            conseq=conseq, 
            alt=alt)

def expr_mult(nexpr1, nexpr2):
    return union_t(union_t.expr.mult, 
            nexpr1=nexpr1, 
            nexpr2=nexpr2)

def expr_subr1(nexp):
    return union_t(union_t.expr.subr1, 
            nexp=nexp)

def expr_zero(nexp):
    return union_t(union_t.expr.zero, 
            nexp=nexp)

def expr_letcc(body):
    return union_t(union_t.expr.letcc, 
            body=body)

def expr_throw(kexp, vexp):
    return union_t(union_t.expr.throw, 
            kexp=kexp, 
            vexp=vexp)

def expr_let(exp, body):
    return union_t(union_t.expr.let, 
            exp=exp, 
            body=body)

def expr_lambda(body):
    return union_t(union_t.expr._lambda, 
            body=body)

def expr_app(rator, rr2):
    return union_t(union_t.expr.app, 
            rator=rator, 
            rr2=rr2)

# Generate functions
def apply_closure():
    global g_pc
    global g__env_
    global g__rr2_
    global g__exp_

    match g__rator_.type:
        case union_t.clos.closure:
            body = g__rator_.body
            env_cps = g__rator_.env_cps
            g__exp_ = body
            g__env_ = envr_extend_env(g__rr2_, env_cps)
            g_pc = value_of_cps

def apply_env():
    global g_pc
    global g__vari_
    global g__env_
    global g__v_

    match g__env_.type:
        case union_t.envr.empty:
            raise RuntimeError("unbound identifier")

        case union_t.envr.extend_env:
            re = g__env_.v
            env_cap = g__env_.env
            if (g__vari_ == 0):
                g__v_ = re
                g_pc = apply_k
            else:
                g__env_ = env_cap
                g__vari_ = (g__vari_ - 1)
                g_pc = apply_env

def apply_k():
    global g_pc
    global g__k_
    global g__rator_
    global g__rr2_
    global g__v_
    global g__env_
    global g__exp_

    match g__k_.type:
        case union_t.kt.empty_k:
            dismount = g__k_.dismount
            dismount.switch()

        case union_t.kt.subr1_k:
            k_cap = g__k_.k
            g__k_ = k_cap
            g__v_ = (g__v_ - 1)
            g_pc = apply_k

        case union_t.kt.zero_k:
            k_cap = g__k_.k
            g__k_ = k_cap
            g__v_ = (g__v_ == 0)
            g_pc = apply_k

        case union_t.kt.mult_outer_k:
            env_cps = g__k_.env_cps
            x = g__k_.x
            k_cap = g__k_.k
            g__exp_ = x
            g__env_ = env_cps
            g__k_ = kt_mult_inner_k(g__v_, k_cap)
            g_pc = value_of_cps

        case union_t.kt.mult_inner_k:
            v_cap = g__k_.v
            k_cap = g__k_.k
            g__k_ = k_cap
            g__v_ = v_cap * g__v_
            g_pc = apply_k

        case union_t.kt.if_k:
            env_cps = g__k_.env_cps
            conseq = g__k_.conseq
            alt = g__k_.alt
            k_cap = g__k_.k
            if g__v_:
                g__exp_ = conseq
                g__env_ = env_cps
                g__k_ = k_cap
                g_pc = value_of_cps
            else:
                g__exp_ = alt
                g__env_ = env_cps
                g__k_ = k_cap
                g_pc = value_of_cps

        case union_t.kt.throw_outer_k:
            env_cps = g__k_.env_cps
            k_exp = g__k_.k_exp
            g__exp_ = k_exp
            g__env_ = env_cps
            g__k_ = kt_throw_inner_k(g__v_, g__env_)
            g_pc = value_of_cps

        case union_t.kt.throw_inner_k:
            v = g__k_.v
            env_cps = g__k_.env_cps
            g__k_ = v
            g_pc = apply_k

        case union_t.kt.let_k:
            env_cps = g__k_.env_cps
            body = g__k_.body
            k_cap = g__k_.k
            g__exp_ = body
            g__env_ = envr_extend_env(g__v_, env_cps)
            g__k_ = k_cap
            g_pc = value_of_cps

        case union_t.kt.app_outer_k:
            env_cps = g__k_.env_cps
            rr2 = g__k_.rr2
            k_cap = g__k_.k
            g__exp_ = rr2
            g__env_ = env_cps
            g__k_ = kt_app_inner_k(g__v_, k_cap)
            g_pc = value_of_cps

        case union_t.kt.app_inner_k:
            v_cap = g__k_.v
            k_cap = g__k_.k
            g__rr2_ = g__v_
            g__rator_ = v_cap
            g__k_ = k_cap
            g_pc = apply_closure

def value_of_cps():
    global g_pc
    global g__k_
    global g__env_
    global g__exp_
    global g__v_
    global g__vari_

    match g__exp_.type:
        case union_t.expr.const:
            cexp = g__exp_.cexp
            g__v_ = cexp
            g_pc = apply_k

        case union_t.expr.mult:
            nexpr1 = g__exp_.nexpr1
            nexpr2 = g__exp_.nexpr2
            g__exp_ = nexpr1
            g__k_ = kt_mult_outer_k(g__env_, nexpr2, g__k_)
            g_pc = value_of_cps

        case union_t.expr.subr1:
            nexp = g__exp_.nexp
            g__exp_ = nexp
            g__k_ = kt_subr1_k(g__k_)
            g_pc = value_of_cps

        case union_t.expr.zero:
            nexp = g__exp_.nexp
            g__exp_ = nexp
            g__k_ = kt_zero_k(g__k_)
            g_pc = value_of_cps

        case union_t.expr._if:
            test = g__exp_.test
            conseq = g__exp_.conseq
            alt = g__exp_.alt
            g__exp_ = test
            g__k_ = kt_if_k(g__env_, conseq, alt, g__k_)
            g_pc = value_of_cps

        case union_t.expr.letcc:
            body = g__exp_.body
            g__exp_ = body
            g__env_ = envr_extend_env(g__k_, g__env_)
            g_pc = value_of_cps

        case union_t.expr.throw:
            kexp = g__exp_.kexp
            vexp = g__exp_.vexp
            g__exp_ = kexp
            g__k_ = kt_throw_outer_k(g__env_, vexp)
            g_pc = value_of_cps

        case union_t.expr.let:
            expr = g__exp_.exp
            body = g__exp_.body
            g__exp_ = expr
            g__k_ = kt_let_k(g__env_, body, g__k_)
            g_pc = value_of_cps

        case union_t.expr.var:
            n = g__exp_.n
            g__vari_ = n
            g_pc = apply_env

        case union_t.expr._lambda:
            body = g__exp_.body
            g__v_ = clos_closure(body, g__env_)
            g_pc = apply_k

        case union_t.expr.app:
            rator = g__exp_.rator
            rr2 = g__exp_.rr2
            g__exp_ = rator
            g__k_ = kt_app_outer_k(g__env_, rr2, g__k_)
            g_pc = value_of_cps

def mount_tram():
    global g_pc
    global g__k_
    global _dismount_blank
    g__k_= kt_empty_k(_dismount_blank)

    while True:
        greenlet(g_pc).switch()


def racket_printf(s, *args):
    import re
    print(re.sub(r"~[a-z]", lambda x: "{}", s).format(*args))

if __name__ == '__main__':
    def _blank():
        pass
    jump_mount_tram = greenlet(mount_tram)
    _dismount_blank = greenlet(_blank)
    g__exp_ = expr_let(    expr_lambda(    expr_lambda(    expr_if(    expr_zero(    expr_var(0)),     expr_const(1),     expr_mult(    expr_var(0),     expr_app(    expr_app(    expr_var(1),     expr_var(1)),     expr_subr1(    expr_var(0))))))),     expr_mult(    expr_letcc(    expr_app(    expr_app(    expr_var(1),     expr_var(1)),     expr_throw(    expr_var(0),     expr_app(    expr_app(    expr_var(1),     expr_var(1)),     expr_const(4))))),     expr_const(5)))
    g__env_ = envr_empty()
    g_pc = value_of_cps
    jump_mount_tram.switch()
    racket_printf("Fact 5: ~s\n", g__v_)
