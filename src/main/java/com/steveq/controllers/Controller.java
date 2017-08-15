package com.steveq.controllers;

import com.steveq.communication.models.FromClientRequest;
import com.steveq.communication.models.ToClientResponse;

/**
 * Created by Adam on 2017-08-03.
 */
public interface Controller {
    ToClientResponse processRequest(FromClientRequest request);
}
