package com.kakz.tradeIn.service;

import com.kakz.tradeIn.domain.WalletTransactionType;
import com.kakz.tradeIn.model.Wallet;
import com.kakz.tradeIn.model.WalletTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService{
    @Override
    public WalletTransaction createTransaction(Wallet wallet, WalletTransactionType type, String transferId, String purpose, Long amount) {
        return null;
    }

    @Override
    public List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type) {
        return List.of();
    }
}
