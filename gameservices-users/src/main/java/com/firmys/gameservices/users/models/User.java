package com.firmys.gameservices.users.models;

import com.firmys.gameservices.common.CommonEntity;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record User(@With @Id UUID uuid, @NotEmpty String firstName, @NotEmpty String lastName)
    implements CommonEntity {}
