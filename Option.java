public enum Option
{
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    LOOK("look"),
    EAT("eat"),
    BACK("back"),
    TAKE("take"),
    DROP("drop"),
    ITEMS("items"),
    FINISH("finish"),
    UNKNOWN("unknown");
    
    private String nombreComando;
    
    private Option(String nombreComando) {
        this.nombreComando = nombreComando;
    }
    
    public String getOptionString() {
        return nombreComando;
    }
}
