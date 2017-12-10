package main.com.jlaotsezu.library.quan_ly_sach.domain.specifications;

import main.com.jlaotsezu.library.data.Specification;

public class FindBookImportRequestByLibrarianId extends Specification {
    private int librarianId;
    public FindBookImportRequestByLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public int getLibrarianId() {
        return librarianId;
    }
}
