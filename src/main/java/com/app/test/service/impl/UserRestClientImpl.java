package com.app.test.service.impl;

import com.app.test.exception.UserServiceException;
import com.app.test.models.User;
import com.app.test.service.UserRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.app.test.constants.Constants.*;


@Service
public class UserRestClientImpl implements UserRestClient {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @Override
    public List<User> getAllUsers() throws UserServiceException {
        String uri = env.getProperty(USERS_API);
        User[] users;
        try {
            users = restTemplate.getForObject(uri, User[].class);
        } catch (HttpClientErrorException ex) {
            throw new UserServiceException(ex.getMessage());
        } catch (HttpServerErrorException ex) {
            throw new UserServiceException(ex.getMessage());
        } catch (RestClientException ex) {
            throw new UserServiceException(ex.getMessage());
        }

        return !Objects.isNull(users) ? Arrays.asList(users) : Collections.EMPTY_LIST;
    }

    @Override
    public List<User> getAllCityUsers(String city) throws UserServiceException {
        String uri = env.getProperty(USERS_CITY_API);
        Map<String, String> params = new HashMap();
        params.put(CITY, city);
        User[] usersLondon;
        try {
            usersLondon = restTemplate.getForObject(uri, User[].class, params);
        } catch (HttpClientErrorException ex) {
            throw new UserServiceException(ex.getMessage());
        } catch (HttpServerErrorException ex) {
            throw new UserServiceException(ex.getMessage());
        } catch (RestClientException ex) {
            throw new UserServiceException(ex.getMessage());
        }

        return !Objects.isNull(usersLondon) ? Arrays.asList(usersLondon) : Collections.EMPTY_LIST;
    }
}
