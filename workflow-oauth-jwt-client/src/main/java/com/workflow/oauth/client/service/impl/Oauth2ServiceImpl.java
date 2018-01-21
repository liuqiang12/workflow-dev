package com.workflow.oauth.client.service.impl;


import com.workflow.oauth.client.dto.*;
import com.workflow.oauth.client.infrastructure.httpclient.HttpClientExecutor;
import com.workflow.oauth.client.infrastructure.httpclient.HttpClientPostExecutor;
import com.workflow.oauth.client.service.Oauth2Service;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("oauth2Service")
public class Oauth2ServiceImpl implements Oauth2Service {

    private static final Logger LOG = LoggerFactory.getLogger(Oauth2ServiceImpl.class);


    @Value("${access-token-uri}")
    private String accessTokenUri;

    @Value("${unityUserInfoUri}")
    private String unityUserInfoUri;


    @Override
    public AccessTokenDto retrieveAccessTokenDto(AuthAccessTokenDto tokenDto) {
        final String fullUri = tokenDto.getAccessTokenUri();
        LOG.debug("Get access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, tokenDto.getAuthCodeParams());
    }

    @Override
    public AuthAccessTokenDto createAuthAccessTokenDto(AuthCallbackDto callbackDto) {
        return new AuthAccessTokenDto()
                .setAccessTokenUri(accessTokenUri)
                .setCode(callbackDto.getCode());
    }

    @Override
    public UserDto loadUnityUserDto(String accessToken) {
        LOG.debug("Load Unity-User_Info by access_token = {}", accessToken);

        if (StringUtils.isEmpty(accessToken)) {
            return new UserDto("Illegal 'access_token'", "'access_token' is empty");
        } else {
            HttpClientExecutor executor = new HttpClientExecutor(unityUserInfoUri);
            executor.addRequestParam("access_token", accessToken);

            UserDtoResponseHandler responseHandler = new UserDtoResponseHandler();
            executor.execute(responseHandler);

            return responseHandler.getUserDto();
        }

    }

    @Override
    public AccessTokenDto retrievePasswordAccessTokenDto(AuthAccessTokenDto authAccessTokenDto) {
        final String fullUri = authAccessTokenDto.getAccessTokenUri();
        LOG.debug("Get [password] access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, authAccessTokenDto.getAccessTokenParams());
    }

    @Override
    public AccessTokenDto refreshAccessTokenDto(RefreshAccessTokenDto refreshAccessTokenDto) {
        final String fullUri = refreshAccessTokenDto.getRefreshAccessTokenUri();
        LOG.debug("Get refresh_access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, refreshAccessTokenDto.getRefreshTokenParams());
    }

    @Override
    public AccessTokenDto retrieveCredentialsAccessTokenDto(AuthAccessTokenDto authAccessTokenDto) {
        final String uri = authAccessTokenDto.getAccessTokenUri();
        LOG.debug("Get [{}] access_token URL: {}", authAccessTokenDto.getGrantType(), uri);

        return loadAccessTokenDto(uri, authAccessTokenDto.getCredentialsParams());
    }


    private AccessTokenDto loadAccessTokenDto(String fullUri, Map<String, String> params) {
        HttpClientExecutor executor = new HttpClientPostExecutor(fullUri);
        for (String key : params.keySet()) {
            executor.addRequestParam(key, params.get(key));
        }

        AccessTokenResponseHandler responseHandler = new AccessTokenResponseHandler();
        executor.execute(responseHandler);

        return responseHandler.getAccessTokenDto();
    }
}
