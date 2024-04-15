package com.mandarin.dto;

public enum Action {
    //TODO: Make those two separate endpoints, since Action are supposed to be for mutable calls.
//    RETRIEVE_AVAILABLE_FEATURES,
//    RETRIEVE_FEATURE_SUBSCRIBERS,
    SUSPEND_FEATURE,
    CONFIGURE_FEATURE,
    SUBSCRIBE_SERVER_TO_FEATURE,
    UNSUBSCRIBE_SERVER_FROM_FEATURE
}
