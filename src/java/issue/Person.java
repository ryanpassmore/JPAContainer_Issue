/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package issue;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

/**
 *
 * @author Ryan
 */
@Entity
public class Person 
{
    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
