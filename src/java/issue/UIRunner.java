/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package issue;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.Stack;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ryan
 */
public class UIRunner extends UI
{
    private Stack<Table> tables = new Stack<Table>();

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        
        setContent(layout);
        
        Button addTable = new Button("Add Table", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                JPAContainer container = JPAContainerFactory.makeReadOnly(Person.class, "jpacontainer_issue");
                                
                Table t = new Table();
                t.setContainerDataSource(container);
                
                layout.addComponent(t);              
                tables.push(t);
            }
        });
        layout.addComponent(addTable);
        
        Button removeTable = new Button("Remove Table", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                layout.removeComponent(tables.pop());
            }
        });
        layout.addComponent(removeTable);
    }    
}
