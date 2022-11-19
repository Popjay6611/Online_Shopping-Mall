package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.EmailDetails;

public interface IEmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
