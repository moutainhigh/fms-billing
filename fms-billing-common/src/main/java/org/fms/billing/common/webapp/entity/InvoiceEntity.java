package org.fms.billing.common.webapp.entity;

import java.util.List;

import org.fms.billing.common.webapp.domain.NoteInfoDomain;

//调用桂东发票实体
public class InvoiceEntity {
    private List<NoteInfoDomain> noteInfoDomains;
    private Integer kplx;

    public List<NoteInfoDomain> getNoteInfoDomains() {
        return noteInfoDomains;
    }

    public void setNoteInfoDomains(List<NoteInfoDomain> noteInfoDomains) {
        this.noteInfoDomains = noteInfoDomains;
    }

    public Integer getKplx() {
        return kplx;
    }

    public void setKplx(Integer kplx) {
        this.kplx = kplx;
    }
}
