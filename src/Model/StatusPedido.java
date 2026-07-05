package Model;

public enum StatusPedido {
    AGUARDANDO_CONFIRMACAO,
    CONFIRMADO,
    EM_PREPARO,
    SAIU_PARA_ENTREGA,
    ENTREGUE,
    CANCELADO;

    public StatusPedido proximo() {
        switch (this) {
            case AGUARDANDO_CONFIRMACAO: return CONFIRMADO;
            case CONFIRMADO: return EM_PREPARO;
            case EM_PREPARO: return SAIU_PARA_ENTREGA;
            case SAIU_PARA_ENTREGA: return ENTREGUE;
            default: return null;
        }
    }
    public boolean isFinal() {
        return this == ENTREGUE || this == CANCELADO;
    }
}
