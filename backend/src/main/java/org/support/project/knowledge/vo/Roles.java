package org.support.project.knowledge.vo;

import org.support.project.web.entity.RolesEntity;

public class Roles extends RolesEntity {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;

    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
