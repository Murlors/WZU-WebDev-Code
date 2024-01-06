package service;

import entity.Email;

import java.util.List;

public interface EmailService extends PagingService {
    void addEmail(Email email);
    List<Email> findEmailByRecipient(String recipient);

    int getTotalRows(String recipient);

    List<Email> findAllEmailsPageable(int pageNow, int pageCount, String recipient);
}
