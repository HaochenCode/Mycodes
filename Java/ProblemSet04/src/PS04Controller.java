public class PS04Controller implements Observer{
    private PS04Model mod;
    private PS04View view;
    public PS04Controller(PS04Model p, PS04View v){
        mod = p;
        view = v;
    }

    @Override
    public void update() {
        Int2DArray arr =mod.getArray();
        view.getDataAndVisualizeIt();
    }
}
