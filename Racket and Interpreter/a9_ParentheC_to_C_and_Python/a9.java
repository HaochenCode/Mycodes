import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// Define the registers
class RegistersGlobal {
    static Object Register_EXP = null;
    static Object Register_ENV = null;
    static Object Register_K = null;
    static Object Register_RATOR = null;
    static Object Register_RR2 = null;
    static Object Register_V = null;
    static Object Register_VARI = null;

    // Define the program counter
    static Callable<Void> Register_PC = null;
}

// Define the union classes, functions and enums
class UnionEnums {
    enum expr {
       CONST,
       VAR,
       _IF,
       MULT,
       SUBR1,
       ZERO,
       LETCC,
       THROW,
       LET,
       _LAMBDA,
       APP,
    }

    enum clos {
       CLOSURE,
    }

    enum envr {
       EMPTY,
       EXTEND_ENV,
    }

    enum kt {
       EMPTY_K,
       SUBR1_K,
       ZERO_K,
       MULT_OUTER_K,
       MULT_INNER_K,
       IF_K,
       LET_K,
       THROW_OUTER_K,
       THROW_INNER_K,
       APP_OUTER_K,
       APP_INNER_K,
    }

}

// Define the union classes
class UnionType extends Object {

    private Object type = null;
    private HashMap<String, Object> properties = new HashMap<>();

    public Object getType() {
        return type;
    }

    public Object get(String property) {
        return this.properties.get(property);
    }

    public UnionType(Object type, HashMap<String, Object> properties) {
        this.type = type;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "UnionType[type = " + type.toString() + ", properties = {"
                + properties.keySet().stream().map((String s) -> {
                    return s + ": " + properties.get(s).toString();
                }).reduce("", (String a, String p) -> {
                    return a + ", " + p;
                }) + "}]";
    }

    // Union functions
    public static UnionType kt_empty_k(Object dismount) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("dismount", dismount);
        return new UnionType(UnionEnums.kt.EMPTY_K, properties);
    }

    public static UnionType kt_subr1_k(Object k) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("k", k);
        return new UnionType(UnionEnums.kt.SUBR1_K, properties);
    }

    public static UnionType kt_zero_k(Object k) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("k", k);
        return new UnionType(UnionEnums.kt.ZERO_K, properties);
    }

    public static UnionType kt_mult_outer_k(Object env_cps, Object x, Object k) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("env_cps", env_cps);
        properties.put("x", x);
        properties.put("k", k);
        return new UnionType(UnionEnums.kt.MULT_OUTER_K, properties);
    }

    public static UnionType kt_mult_inner_k(Object v, Object k) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("v", v);
        properties.put("k", k);
        return new UnionType(UnionEnums.kt.MULT_INNER_K, properties);
    }

    public static UnionType kt_if_k(Object env_cps, Object conseq, Object alt, Object k) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("env_cps", env_cps);
        properties.put("conseq", conseq);
        properties.put("alt", alt);
        properties.put("k", k);
        return new UnionType(UnionEnums.kt.IF_K, properties);
    }

    public static UnionType kt_let_k(Object env_cps, Object body, Object k) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("env_cps", env_cps);
        properties.put("body", body);
        properties.put("k", k);
        return new UnionType(UnionEnums.kt.LET_K, properties);
    }

    public static UnionType kt_throw_outer_k(Object env_cps, Object k_exp) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("env_cps", env_cps);
        properties.put("k_exp", k_exp);
        return new UnionType(UnionEnums.kt.THROW_OUTER_K, properties);
    }

    public static UnionType kt_throw_inner_k(Object v, Object env_cps) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("v", v);
        properties.put("env_cps", env_cps);
        return new UnionType(UnionEnums.kt.THROW_INNER_K, properties);
    }

    public static UnionType kt_app_outer_k(Object env_cps, Object rr2, Object k) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("env_cps", env_cps);
        properties.put("rr2", rr2);
        properties.put("k", k);
        return new UnionType(UnionEnums.kt.APP_OUTER_K, properties);
    }

    public static UnionType kt_app_inner_k(Object v, Object k) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("v", v);
        properties.put("k", k);
        return new UnionType(UnionEnums.kt.APP_INNER_K, properties);
    }

    public static UnionType envr_empty() {
        HashMap<String, Object> properties = new HashMap<>();

        return new UnionType(UnionEnums.envr.EMPTY, properties);
    }

    public static UnionType envr_extend_env(Object v, Object env) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("v", v);
        properties.put("env", env);
        return new UnionType(UnionEnums.envr.EXTEND_ENV, properties);
    }

    public static UnionType clos_closure(Object body, Object env_cps) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("body", body);
        properties.put("env_cps", env_cps);
        return new UnionType(UnionEnums.clos.CLOSURE, properties);
    }

    public static UnionType expr_const(Object cexp) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("cexp", cexp);
        return new UnionType(UnionEnums.expr.CONST, properties);
    }

    public static UnionType expr_var(Object n) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("n", n);
        return new UnionType(UnionEnums.expr.VAR, properties);
    }

    public static UnionType expr_if(Object test, Object conseq, Object alt) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("test", test);
        properties.put("conseq", conseq);
        properties.put("alt", alt);
        return new UnionType(UnionEnums.expr._IF, properties);
    }

    public static UnionType expr_mult(Object nexpr1, Object nexpr2) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("nexpr1", nexpr1);
        properties.put("nexpr2", nexpr2);
        return new UnionType(UnionEnums.expr.MULT, properties);
    }

    public static UnionType expr_subr1(Object nexp) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("nexp", nexp);
        return new UnionType(UnionEnums.expr.SUBR1, properties);
    }

    public static UnionType expr_zero(Object nexp) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("nexp", nexp);
        return new UnionType(UnionEnums.expr.ZERO, properties);
    }

    public static UnionType expr_letcc(Object body) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("body", body);
        return new UnionType(UnionEnums.expr.LETCC, properties);
    }

    public static UnionType expr_throw(Object kexp, Object vexp) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("kexp", kexp);
        properties.put("vexp", vexp);
        return new UnionType(UnionEnums.expr.THROW, properties);
    }

    public static UnionType expr_let(Object exp, Object body) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("exp", exp);
        properties.put("body", body);
        return new UnionType(UnionEnums.expr.LET, properties);
    }

    public static UnionType expr_lambda(Object body) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("body", body);
        return new UnionType(UnionEnums.expr._LAMBDA, properties);
    }

    public static UnionType expr_app(Object rator, Object rr2) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("rator", rator);
        properties.put("rr2", rr2);
        return new UnionType(UnionEnums.expr.APP, properties);
    }


}

// Generate functions
class ContinuationDriver {

    private ExecutorService executorService;

    public ContinuationDriver(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Future<Void> apply_closure() throws Exception {
        UnionType union_object = ((UnionType) RegistersGlobal.Register_RATOR);
        UnionEnums.clos target = (UnionEnums.clos) union_object.getType();

        Object env_cps = null, body = null;

        switch (target) {
            case CLOSURE:
                body = union_object.get("body");
                env_cps = union_object.get("env_cps");
                RegistersGlobal.Register_EXP = body;
                RegistersGlobal.Register_ENV = UnionType.envr_extend_env(RegistersGlobal.Register_RR2, env_cps);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

        }
        return null;
    }

    public Future<Void> apply_env() throws Exception {
        UnionType union_object = ((UnionType) RegistersGlobal.Register_ENV);
        UnionEnums.envr target = (UnionEnums.envr) union_object.getType();

        Object envx = null, re = null;

        switch (target) {
            case EMPTY:
                throw new Exception("unbound identifier");


            case EXTEND_ENV:
                re = union_object.get("v");
                envx = union_object.get("env");
                if ((Boolean) (RegistersGlobal.Register_VARI.equals(0))) {
                    RegistersGlobal.Register_V = re;
                    RegistersGlobal.Register_PC = () -> {
                        this.apply_k();
                        return null;
                    };
                } else {
                    RegistersGlobal.Register_ENV = envx;
                    RegistersGlobal.Register_VARI = ((Integer) RegistersGlobal.Register_VARI - 1);
                    RegistersGlobal.Register_PC = () -> {
                        this.apply_env();
                        return null;
                    };
                }
                break;

        }
        return null;
    }

    public Future<Void> apply_k() throws Exception {
        UnionType union_object = ((UnionType) RegistersGlobal.Register_K);
        UnionEnums.kt target = (UnionEnums.kt) union_object.getType();

        Object k_exp = null, conseq = null, alt = null, v = null, kx = null, body = null, vx = null, x = null, env_cps = null, dismount = null, rr2 = null;

        switch (target) {
            case EMPTY_K:
                dismount = union_object.get("dismount");
                this.executorService.submit((Callable<Void>) jumpout);                break;

            case SUBR1_K:
                kx = union_object.get("k");
                RegistersGlobal.Register_K = kx;
                RegistersGlobal.Register_V = ((Integer) RegistersGlobal.Register_V - 1);
                RegistersGlobal.Register_PC = () -> {
                    this.apply_k();
                    return null;
                };
                break;

            case ZERO_K:
                kx = union_object.get("k");
                RegistersGlobal.Register_K = kx;
                RegistersGlobal.Register_V = RegistersGlobal.Register_V.equals(0);
                RegistersGlobal.Register_PC = () -> {
                    this.apply_k();
                    return null;
                };
                break;

            case MULT_OUTER_K:
                env_cps = union_object.get("env_cps");
                x = union_object.get("x");
                kx = union_object.get("k");
                RegistersGlobal.Register_EXP = x;
                RegistersGlobal.Register_ENV = env_cps;
                RegistersGlobal.Register_K = UnionType.kt_mult_inner_k(RegistersGlobal.Register_V, kx);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case MULT_INNER_K:
                vx = union_object.get("v");
                kx = union_object.get("k");
                RegistersGlobal.Register_K = kx;
                RegistersGlobal.Register_V = (Integer) vx * (Integer) RegistersGlobal.Register_V;
                RegistersGlobal.Register_PC = () -> {
                    this.apply_k();
                    return null;
                };
                break;

            case IF_K:
                env_cps = union_object.get("env_cps");
                conseq = union_object.get("conseq");
                alt = union_object.get("alt");
                kx = union_object.get("k");
                if ((Boolean) (RegistersGlobal.Register_V)) {
                    RegistersGlobal.Register_EXP = conseq;
                    RegistersGlobal.Register_ENV = env_cps;
                    RegistersGlobal.Register_K = kx;
                    RegistersGlobal.Register_PC = () -> {
                        this.value_of_cps();
                        return null;
                    };
                } else {
                    RegistersGlobal.Register_EXP = alt;
                    RegistersGlobal.Register_ENV = env_cps;
                    RegistersGlobal.Register_K = kx;
                    RegistersGlobal.Register_PC = () -> {
                        this.value_of_cps();
                        return null;
                    };
                }
                break;

            case THROW_OUTER_K:
                env_cps = union_object.get("env_cps");
                k_exp = union_object.get("k_exp");
                RegistersGlobal.Register_EXP = k_exp;
                RegistersGlobal.Register_ENV = env_cps;
                RegistersGlobal.Register_K = UnionType.kt_throw_inner_k(RegistersGlobal.Register_V, RegistersGlobal.Register_ENV);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case THROW_INNER_K:
                v = union_object.get("v");
                env_cps = union_object.get("env_cps");
                RegistersGlobal.Register_K = RegistersGlobal.Register_V;
                RegistersGlobal.Register_PC = () -> {
                    this.apply_k();
                    return null;
                };
                break;

            case LET_K:
                env_cps = union_object.get("env_cps");
                body = union_object.get("body");
                kx = union_object.get("k");
                RegistersGlobal.Register_EXP = body;
                RegistersGlobal.Register_ENV = UnionType.envr_extend_env(RegistersGlobal.Register_V, env_cps);
                RegistersGlobal.Register_K = kx;
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case APP_OUTER_K:
                env_cps = union_object.get("env_cps");
                rr2 = union_object.get("rr2");
                kx = union_object.get("k");
                RegistersGlobal.Register_EXP = RegistersGlobal.Register_RR2;
                RegistersGlobal.Register_ENV = env_cps;
                RegistersGlobal.Register_K = UnionType.kt_app_inner_k(RegistersGlobal.Register_V, kx);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case APP_INNER_K:
                vx = union_object.get("v");
                kx = union_object.get("k");
                RegistersGlobal.Register_RR2 = RegistersGlobal.Register_V;
                RegistersGlobal.Register_RATOR = vx;
                RegistersGlobal.Register_K = kx;
                RegistersGlobal.Register_PC = () -> {
                    this.apply_closure();
                    return null;
                };
                break;

        }
        return null;
    }

    public Future<Void> value_of_cps() throws Exception {
        UnionType union_object = ((UnionType) RegistersGlobal.Register_EXP);
        UnionEnums.expr target = (UnionEnums.expr) union_object.getType();

        Object vexp = null, cexp = null, rator = null, expr = null, nexp = null, test = null, conseq = null, n = null, nexpr2 = null, nexpr1 = null, kexp = null, alt = null, body = null, rr2 = null;

        switch (target) {
            case CONST:
                cexp = union_object.get("cexp");
                RegistersGlobal.Register_V = cexp;
                RegistersGlobal.Register_PC = () -> {
                    this.apply_k();
                    return null;
                };
                break;

            case MULT:
                nexpr1 = union_object.get("nexpr1");
                nexpr2 = union_object.get("nexpr2");
                RegistersGlobal.Register_EXP = nexpr1;
                RegistersGlobal.Register_K = UnionType.kt_mult_outer_k(RegistersGlobal.Register_ENV, nexpr2, RegistersGlobal.Register_K);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case SUBR1:
                nexp = union_object.get("nexp");
                RegistersGlobal.Register_EXP = nexp;
                RegistersGlobal.Register_K = UnionType.kt_subr1_k(RegistersGlobal.Register_K);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case ZERO:
                nexp = union_object.get("nexp");
                RegistersGlobal.Register_EXP = nexp;
                RegistersGlobal.Register_K = UnionType.kt_zero_k(RegistersGlobal.Register_K);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case _IF:
                test = union_object.get("test");
                conseq = union_object.get("conseq");
                alt = union_object.get("alt");
                RegistersGlobal.Register_EXP = test;
                RegistersGlobal.Register_K = UnionType.kt_if_k(RegistersGlobal.Register_ENV, conseq, alt, RegistersGlobal.Register_K);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case LETCC:
                body = union_object.get("body");
                RegistersGlobal.Register_EXP = body;
                RegistersGlobal.Register_ENV = UnionType.envr_extend_env(RegistersGlobal.Register_K, RegistersGlobal.Register_ENV);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case THROW:
                kexp = union_object.get("kexp");
                vexp = union_object.get("vexp");
                RegistersGlobal.Register_EXP = kexp;
                RegistersGlobal.Register_K = UnionType.kt_throw_outer_k(RegistersGlobal.Register_ENV, vexp);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case LET:
                expr = union_object.get("exp");
                body = union_object.get("body");
                RegistersGlobal.Register_EXP = expr;
                RegistersGlobal.Register_K = UnionType.kt_let_k(RegistersGlobal.Register_ENV, body, RegistersGlobal.Register_K);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

            case VAR:
                n = union_object.get("n");
                RegistersGlobal.Register_VARI = n;
                RegistersGlobal.Register_PC = () -> {
                    this.apply_env();
                    return null;
                };
                break;

            case _LAMBDA:
                body = union_object.get("body");
                RegistersGlobal.Register_V = UnionType.clos_closure(body, RegistersGlobal.Register_ENV);
                RegistersGlobal.Register_PC = () -> {
                    this.apply_k();
                    return null;
                };
                break;

            case APP:
                rator = union_object.get("rator");
                rr2 = union_object.get("rr2");
                RegistersGlobal.Register_EXP = RegistersGlobal.Register_RATOR;
                RegistersGlobal.Register_K = UnionType.kt_app_outer_k(RegistersGlobal.Register_ENV, RegistersGlobal.Register_RR2, RegistersGlobal.Register_K);
                RegistersGlobal.Register_PC = () -> {
                    this.value_of_cps();
                    return null;
                };
                break;

        }
        return null;
    }

    void mount_tram() {
        RegistersGlobal.Register_K = UnionType.kt_empty_k((Callable<Void>) (() -> {
            this.executorService.shutdown();
            return null;
        }));

        trampoline();
    }

    void trampoline() {
        this.executorService.submit(RegistersGlobal.Register_PC);
        this.executorService.submit(() -> {
            trampoline();
            return null;
        });
    }

    void waitUntilTrampolineTermination() throws InterruptedException {
        this.executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}


public class a9 {

    private ContinuationDriver continuationDriver;

    public a9() {
        this.continuationDriver = new ContinuationDriver(Executors.newSingleThreadExecutor());
    }

    public static void main(String[] args) throws InterruptedException {
        a9 application = new a9();
        RegistersGlobal.Register_EXP = UnionType.expr_let(        UnionType.expr_lambda(        UnionType.expr_lambda(        UnionType.expr_if(        UnionType.expr_zero(        UnionType.expr_var(0)),         UnionType.expr_const(1),         UnionType.expr_mult(        UnionType.expr_var(0),         UnionType.expr_app(        UnionType.expr_app(        UnionType.expr_var(1),         UnionType.expr_var(1)),         UnionType.expr_subr1(        UnionType.expr_var(0))))))),         UnionType.expr_mult(        UnionType.expr_letcc(        UnionType.expr_app(        UnionType.expr_app(        UnionType.expr_var(1),         UnionType.expr_var(1)),         UnionType.expr_throw(        UnionType.expr_var(0),         UnionType.expr_app(        UnionType.expr_app(        UnionType.expr_var(1),         UnionType.expr_var(1)),         UnionType.expr_const(4))))),         UnionType.expr_const(5)));
        RegistersGlobal.Register_ENV = UnionType.envr_empty();
        RegistersGlobal.Register_PC = () -> {
            application.continuationDriver.value_of_cps();
            return null;
        };
        application.continuationDriver.mount_tram();
        application.continuationDriver.waitUntilTrampolineTermination();
        System.out.printf("Fact 5: %d\n", RegistersGlobal.Register_V);
    }
}
