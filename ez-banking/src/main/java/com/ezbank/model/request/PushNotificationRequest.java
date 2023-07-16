package com.ezbank.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushNotificationRequest {

    private String pushNotificationId;

    private String status;
}
