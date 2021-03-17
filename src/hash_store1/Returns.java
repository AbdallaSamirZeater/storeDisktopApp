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
@Table(name = "returns", catalog = "stocks", schema = "")
@NamedQueries({
    @NamedQuery(name = "Returns.findAll", query = "SELECT r FROM Returns r")
    , @NamedQuery(name = "Returns.findById", query = "SELECT r FROM Returns r WHERE r.id = :id")
    , @NamedQuery(name = "Returns.findByClient", query = "SELECT r FROM Returns r WHERE r.client = :client")
    , @NamedQuery(name = "Returns.findByName", query = "SELECT r FROM Returns r WHERE r.name = :name")
    , @NamedQuery(name = "Returns.findByQuantity", query = "SELECT r FROM Returns r WHERE r.quantity = :quantity")
    , @NamedQuery(name = "Returns.findByPrice", query = "SELECT r FROM Returns r WHERE r.price = :price")
    , @NamedQuery(name = "Returns.findByTotal", query = "SELECT r FROM Returns r WHERE r.total = :total")
    , @NamedQuery(name = "Returns.findByPaid", query = "SELECT r FROM Returns r WHERE r.paid = :paid")
    , @NamedQuery(name = "Returns.findByRest", query = "SELECT r FROM Returns r WHERE r.rest = :rest")})
public class Returns implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "client")
    private String client;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "price")
    private float price;
    @Basic(optional = false)
    @Column(name = "total")
    private float total;
    @Basic(optional = false)
    @Column(name = "paid")
    private float paid;
    @Basic(optional = false)
    @Column(name = "rest")
    private float rest;

    public Returns() {
    }

    public Returns(Integer id) {
        this.id = id;
    }

    public Returns(Integer id, String client, String name, int quantity, float price, float total, float paid, float rest) {
        this.id = id;
        this.client = client;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.paid = paid;
        this.rest = rest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        String oldClient = this.client;
        this.client = client;
        changeSupport.firePropertyChange("client", oldClient, client);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        int oldQuantity = this.quantity;
        this.quantity = quantity;
        changeSupport.firePropertyChange("quantity", oldQuantity, quantity);
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        float oldPrice = this.price;
        this.price = price;
        changeSupport.firePropertyChange("price", oldPrice, price);
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        float oldTotal = this.total;
        this.total = total;
        changeSupport.firePropertyChange("total", oldTotal, total);
    }

    public float getPaid() {
        return paid;
    }

    public void setPaid(float paid) {
        float oldPaid = this.paid;
        this.paid = paid;
        changeSupport.firePropertyChange("paid", oldPaid, paid);
    }

    public float getRest() {
        return rest;
    }

    public void setRest(float rest) {
        float oldRest = this.rest;
        this.rest = rest;
        changeSupport.firePropertyChange("rest", oldRest, rest);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Returns)) {
            return false;
        }
        Returns other = (Returns) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hash_store1.Returns[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
