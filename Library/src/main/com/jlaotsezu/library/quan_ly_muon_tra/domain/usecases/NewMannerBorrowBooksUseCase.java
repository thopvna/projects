package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindLoanByUserId;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.Role;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopyStatus;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

public class NewMannerBorrowBooksUseCase extends UseCase<NewMannerBorrowBooksUseCase.RequestValues, NewMannerBorrowBooksUseCase.ResponseValues> {
    public static final String BORROWCARD_IS_NOT_EXISTING = "Thẻ mượn không tồn tại.";
    public static final String LIBRARIAN_IS_NOT_EXISTING = "Thủ thư không tồn tại.";
    public static final String USER_WITH_LIBRARIAN_ID_IS_NOT_A_LIRARIAN = "Người dùng hiện tại không phải thủ thư.";
    public static final String USER_WITH_BORROWER_ID_IS_DON_T_HAVE_ANY_BORROW_CARD = "Người mượn không có thẻ mượn nào.";
    public static final String INPUT_INVALID_DON_T_HAVE_ANY_BOOK_COPY = "Đầu vào không hợp lệ. Không có bản sao book nào.";
    public static final String USER_DON_T_HAVE_ANY_CARD_AVAILABLE = "Người mượn không có thẻ mượn nào hợp lệ.";
    public static final String BORROW_FAILED_INTERNAL_DB_ERROR = "Mượn sách thất bại. Lỗi cơ sở dữ liệu.";
    public static final String SOME_BOOK_COPY_UNBORROWABLE = "Một vài bản sao sách không thể cho mượn.";
    public static final String SOME_BOOK_COPY_IS_NOT_EXISTING = "Một vài bản sao sách không tồn tại.";
    public static final String INPUT_INVALID_BOOK_COPY_AMOUNT_5 = "Đầu vào không hợp lệ. Số sách không thể quá 5 cuốn";
    public static final String BORROW_CARD_IS_EXPIRED = "Thẻ mượn đã hết hạn";
    public static final String BORROW_CARD_IS_DISABLED = "Thẻ mượn đang bị vô hiệu hóa";
    public static final String BORROWER_ALREADY_BORROWING = "Người dùng đã mượn sách. Nhưng vẫn chưa trả.";

    private Repository<Integer, User> userRepository;
    private Repository<Integer, BookCopy> bookCopyRepository;
    private Repository<Integer, BorrowCard> borrowCardRepository;
    private Repository<Integer, Loan> loanRepository;

    @Autowired
    public NewMannerBorrowBooksUseCase(Repository<Integer, User> userRepository, Repository<Integer, BookCopy> bookCopyRepository, Repository<Integer, BorrowCard> borrowCardRepository, Repository<Integer, Loan> loanRepository) {
        this.userRepository = userRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.borrowCardRepository = borrowCardRepository;
        this.loanRepository = loanRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int borrowCardId = requestValues.getBorrowCardId();
        int librarianId = requestValues.getLibrarianId();
        List<Integer> bookCopies = requestValues.getBookCopyIds();

        BorrowCard borrowCard = borrowCardRepository.fetchById(borrowCardId);
        User librarian = userRepository.fetchById(librarianId);
        if(bookCopies == null || bookCopies.size() == 0){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_DON_T_HAVE_ANY_BOOK_COPY));
        }
        else if(bookCopies.size() > 5){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_BOOK_COPY_AMOUNT_5));
        }
        else if(borrowCard == null){
            callback.onCompleted(ComplexResponse.fail(BORROWCARD_IS_NOT_EXISTING));
        }
        else if(librarian == null){
            callback.onCompleted(ComplexResponse.fail(LIBRARIAN_IS_NOT_EXISTING));
        }
        else {
            boolean librarianNotAvailable = librarian.getRoles() == null || !librarian.getRoles().contains(Role.LIBRARICIAN);
            if(librarianNotAvailable){
                callback.onCompleted(ComplexResponse.fail(USER_WITH_LIBRARIAN_ID_IS_NOT_A_LIRARIAN));
            }
            else if(borrowerBorrowing(borrowCard.getBorrowerId())){
                callback.onCompleted(ComplexResponse.fail(BORROWER_ALREADY_BORROWING));
            }
            else{
                if(borrowCard.isExpired()){
                   callback.onCompleted(ComplexResponse.fail(BORROW_CARD_IS_EXPIRED));
                }
                else if(!borrowCard.isActive()){
                    callback.onCompleted(ComplexResponse.fail(BORROW_CARD_IS_DISABLED));
                }
                else {
                    confirm(librarian, borrowCard, bookCopies, callback);
                }
            }
        }
    }

    private boolean borrowerBorrowing(int borrowerId) {
        List<Loan> loan = loanRepository.find(new FindLoanByUserId(borrowerId));
        return loan != null && loan.size() > 0;
    }

    private void confirm(User librarian, BorrowCard card, List<Integer> bookCopyIds, UseCaseCallback<ResponseValues> callback) {
        boolean allBookCopyExisting = true;
        boolean allBookCopyBorrowable = true;
        List<BookCopy> bookCopies = new LinkedList<>();
        for(int bookCopyId : bookCopyIds){
            BookCopy bookCopy = bookCopyRepository.fetchById(bookCopyId);
            if(bookCopy == null){
                allBookCopyExisting = false;
                break;
            }
            else{
                bookCopies.add(bookCopy);
                if(!bookCopy.borrowable()){
                    allBookCopyBorrowable = false;
                    break;
                }
            }
        }
        if(!allBookCopyExisting){
            callback.onCompleted(ComplexResponse.fail(SOME_BOOK_COPY_IS_NOT_EXISTING));
        }
        else if(!allBookCopyBorrowable){
            callback.onCompleted(ComplexResponse.fail(SOME_BOOK_COPY_UNBORROWABLE));
        }
        else{
            for(BookCopy bookCopy : bookCopies){
                bookCopy.setBookCopyStatus(BookCopyStatus.BORROWED);
            }
            Loan loan = new Loan(librarian, card.getUser(), bookCopies);
            boolean success = bookCopyRepository.updateAll(bookCopies) && loanRepository.save(loan);
            if(success){
                callback.onCompleted(ComplexResponse.success(new ResponseValues(loan)));
            }
            else{
                callback.onCompleted(ComplexResponse.fail(BORROW_FAILED_INTERNAL_DB_ERROR));
            }
        }
    }
    public static class RequestValues extends UseCase.RequestValues {
        private int librarianId;
        private int borrowCardId;
        private List<Integer> bookCopyIds;

        public RequestValues(int librarianId, int borrowCardId, List<Integer> bookCopyIds) {
            this.librarianId = librarianId;
            this.borrowCardId = borrowCardId;
            this.bookCopyIds = bookCopyIds;
        }

        public int getBorrowCardId() {
            return borrowCardId;
        }

        public int getLibrarianId() {
            return librarianId;
        }

        public List<Integer> getBookCopyIds() {
            return bookCopyIds;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private Loan loan;

        public Loan getLoan() {
            return loan;
        }

        public ResponseValues(Loan loan) {
            this.loan = loan;
        }
    }
}