package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications;

import main.com.jlaotsezu.library.data.Specification;

public class FindBookCopiesById extends Specification{
    private int bookCopyId;

    public FindBookCopiesById(int bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public int getBookCopyId() {
        return bookCopyId;
    }
}
