package com.firmys.gameservices.transactions.models;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.transactions.Transactions;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Transaction(
    @With @Id UUID uuid,
    Transactions type,
    // FUNGIBLE entityId
    UUID fungibleId,
    UUID characterId,
    Long delta,
    Long initial,
    Long result,
    LocalDateTime timestamp)
    implements CommonEntity {}
