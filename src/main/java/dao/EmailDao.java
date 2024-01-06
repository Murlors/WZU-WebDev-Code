package dao;

import entity.Email;

import java.util.List;

public interface EmailDao {
    void addEmail(Email email);
    List<Email> findEmailByRecipient(String recipient);

    List<Email> findAllEmailByRecipient(int pageNow, int pageCount, String recipient);

    int getTotalRows(String recipient);
}
