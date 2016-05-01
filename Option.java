public enum Option
{
    GO("andare"),
    QUIT("smettere"),
    HELP("aiuto"),
    LOOK("guarda"),
    EAT("mangiare"),
    BACK("indietro"),
    TAKE("prendere"),
    DROP("cadere"),
    ITEMS("articoli"),
    UNKNOWN("unknown");
    
    private String nombreComando;
    
    private Option(String nombreComando) {
        this.nombreComando = nombreComando;
    }
    
    public String getOptionString() {
        return nombreComando;
    }
}
