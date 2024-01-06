package service;

import dao.EmailDao;
import dao.EmailDaoImpl;
import entity.Email;

import java.util.List;

public class EmailServiceImpl implements EmailService {
    private final EmailDao emailDao = new EmailDaoImpl();

    public void addEmail(Email email) {
        emailDao.addEmail(email);
    }

    @Override
    public List<Email> findEmailByRecipient(String recipient) {
        return emailDao.findEmailByRecipient(recipient);
    }

    @Override
    public List<Email> findAllEmailsPageable(int pageNow, int pageCount, String recipient) {
        return emailDao.findAllEmailByRecipient(pageNow, pageCount, recipient);
    }

    @Override
    public int getTotalRows(String recipient) {
        return emailDao.getTotalRows(recipient);
    }
}
