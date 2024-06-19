package med.voll.api.infra.errores;

public class ValidationDeIntegridad extends RuntimeException {
    public ValidationDeIntegridad(String s) {
        super(s);
    }
}
