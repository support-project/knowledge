package org.support.project.knowledge.control.open;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class EmojiControl extends Control {

    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.web.control.Control#index()
     */
    @Override
    @Get
    public Boundary index() {
        return super.forward("cheatsheet.jsp");
    }

    @Get
    public Boundary people() {
        return super.forward("people.jsp");
    }

    @Get
    public Boundary nature() {
        return super.forward("nature.jsp");
    }

    @Get
    public Boundary objects() {
        return super.forward("objects.jsp");
    }

    @Get
    public Boundary places() {
        return super.forward("places.jsp");
    }

    @Get
    public Boundary symbols() {
        return super.forward("symbols.jsp");
    }

}
