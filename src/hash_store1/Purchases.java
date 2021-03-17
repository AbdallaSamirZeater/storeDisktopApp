/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_store1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ab o da
 */
@Entity
@Table(name = "purchases", catalog = "stocks", schema = "")
@NamedQueries({
    @NamedQuery(name = "Purchases.findAll", query = "SELECT p FROM Purchases p")
    , @NamedQuery(name = "Purchases.findByCheckId", query = "SELECT p FROM Purchases p WHERE p.checkId = :checkId")
    , @NamedQuery(name = "Purchases.findByTotal", query = "SELECT p FROM Purchases p WHERE p.total = :total")
    , @NamedQuery(name = "Purchases.findByPaid", query = "SELECT p FROM Purchases p WHERE p.paid = :paid")
    , @NamedQuery(name = "Purchases.findByRest", query = "SELECT p FROM Purchases p WHERE p.rest = :rest")
    , @NamedQuery(name = "Purchases.findByClientId", query = "SELECT p FROM Purchases p WHERE p.clientId = :clientId")
    , @NamedQuery(name = "Purchases.findByStockId", query = "SELECT p FROM Purchases p WHERE p.stockId = :stockId")})
public class Purchases implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "check_id")
    private Integer checkId;
    @Basic(optional = false)
    @Column(name = "Total")
    private int total;
    @Basic(optional = false)
    @Column(name = "Paid")
    private int paid;
    @Basic(optional = false)
    @Column(name = "Rest")
    private int rest;
    @Basic(optional = false)
    @Column(name = "client_id")
    private int clientId;
    @Basic(optional = false)
    @Column(name = "stock_id")
    private int stockId;

    public Purchases() {
    }

    public Purchases(Integer checkId) {
        this.checkId = checkId;
    }

    public Purchases(Integer checkId, int total, int paid, int rest, int clientId, int stockId) {
        this.checkId = checkId;
        this.total = total;
        this.paid = paid;
        this.rest = rest;
        this.clientId = clientId;
        this.stockId = stockId;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        Integer oldCheckId = this.checkId;
        this.checkId = checkId;
        changeSupport.firePropertyChange("checkId", oldCheckId, checkId);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        int oldTotal = this.total;
        this.total = total;
        changeSupport.firePropertyChange("total", oldTotal, total);
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        int oldPaid = this.paid;
        this.paid = paid;
        changeSupport.firePropertyChange("paid", oldPaid, paid);
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        int oldRest = this.rest;
        this.rest = rest;
        changeSupport.firePropertyChange("rest", oldRest, rest);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        int oldClientId = this.clientId;
        this.clientId = clientId;
        changeSupport.firePropertyChange("clientId", oldClientId, clientId);
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        int oldStockId = this.stockId;
        this.stockId = stockId;
        changeSupport.firePropertyChange("stockId", oldStockId, stockId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (checkId != null ? checkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchases)) {
            return false;
        }
        Purchases other = (Purchases) object;
        if ((this.checkId == null && other.checkId != null) || (this.checkId != null && !this.checkId.equals(other.checkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hash_store1.Purchases[ checkId=" + checkId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
