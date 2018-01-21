package com.workflow.oauth.client.service;


import com.workflow.oauth.client.dto.*;
public interface Oauth2Service {


    AccessTokenDto retrieveAccessTokenDto(AuthAccessTokenDto tokenDto);

    AuthAccessTokenDto createAuthAccessTokenDto(AuthCallbackDto callbackDto);

    UserDto loadUnityUserDto(String accessToken);

    AccessTokenDto retrievePasswordAccessTokenDto(AuthAccessTokenDto authAccessTokenDto);

    AccessTokenDto refreshAccessTokenDto(RefreshAccessTokenDto refreshAccessTokenDto);

    AccessTokenDto retrieveCredentialsAccessTokenDto(AuthAccessTokenDto authAccessTokenDto);
}