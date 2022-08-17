public class LetsCountJavaInstructions {
    public static void main(String[] args) {
        CalculateRuntime cr = new CalculateRuntime();
        cr.countInstructions(10);
        cr.countInstructions(100);
        cr.countInstructions(1000);
    }
}
