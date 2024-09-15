package com.brief;

import com.brief.enums.Status;
import com.brief.model.AccountHolders;
import com.brief.model.Checking;
import com.brief.model.StudentChecking;
import com.brief.repository.*;
import com.brief.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private AccountHoldersRepository accountHoldersRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AdminsRepository adminsRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear un AccountHolder (propietario de la cuenta)
        AccountHolders accountHolder1 = new AccountHolders();
        accountHolder1.setName("Carlos");
        accountHolder1.setDateOfBirth(LocalDate.of(2010, 10, 10));
        accountHoldersRepository.save(accountHolder1);

        // Crear otro AccountHolder (opcional, propietario secundario)
        AccountHolders accountHolder2 = new AccountHolders();
        accountHolder2.setName("Maria");
        accountHolder2.setDateOfBirth(LocalDate.of(1995, 5, 15));
        accountHoldersRepository.save(accountHolder2);

        // Crear la cuenta Checking
        Checking checkingAccount = new Checking();
        checkingAccount.setPrimaryOwner(accountHolder1);
        checkingAccount.setSecondaryOwner(accountHolder2); // Puede ser null si no hay secundario
        checkingAccount.setBalance(BigDecimal.valueOf(1500)); // Establecer el balance inicial
        checkingAccount.setSecretKey("mySecretKey123");
        checkingAccount.setCreationDate(LocalDate.now()); // Fecha de creación
        checkingAccount.setStatus(Status.ACTIVE); // Estado de la cuenta (debe existir un Enum Status)

        // Guardar la cuenta Checking en la base de datos
        checkingRepository.save(checkingAccount);

        System.out.println("Cuenta Checking creada con éxito para el usuario " + accountHolder1.getName());
    }
}
