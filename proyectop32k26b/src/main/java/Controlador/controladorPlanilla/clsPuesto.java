package Controlador.controladorPlanilla;

import java.math.BigDecimal;

public class clsPuesto {
    
    private int puecodigo;
    private String puenombre;
    private BigDecimal puesalarioBase;
    
    public clsPuesto() {}
    
    public clsPuesto(int puecodigo, String puenombre, BigDecimal puesalarioBase) {
        this.puecodigo = puecodigo;
        this.puenombre = puenombre;
        this.puesalarioBase = puesalarioBase;
    }
    
    public int getPuecodigo() {
        return puecodigo;
    }
    
    public void setPuecodigo(int puecodigo) {
        this.puecodigo = puecodigo;
    }
    
    public String getPuenombre() {
        return puenombre;
    }
    
    public void setPuenombre(String puenombre) {
        this.puenombre = puenombre;
    }
    
    public BigDecimal getPuesalarioBase() {
        return puesalarioBase;
    }
    
    public void setPuesalarioBase(BigDecimal puesalarioBase) {
        this.puesalarioBase = puesalarioBase;
    }
    
    @Override
    public String toString() {
        return puenombre;
    }
}