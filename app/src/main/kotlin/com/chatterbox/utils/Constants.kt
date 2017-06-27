package com.chatterbox.utils

import com.chatterbox.BuildConfig

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Constants for the application
 */

object Constants {

    @JvmField
    val CHATTERBOX_PREF_KEY = "chatterbox_prefs"

    @JvmField
    val CHATTERBOX_DB_NAME = "chatterbox.db"

    @JvmField
    val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"

    val INSTANCE_ID_TOKEN_RETRIEVED = "iid_token_retrieved"
    val FRIENDLY_MSG_LENGTH = "friendly_msg_length"

    @JvmField
    val RC_SIGN_IN = 9001

    @JvmField
    val MESSAGES_CHILD = "messages"

    @JvmField
    val REQUEST_INVITE = 1
    val DEFAULT_MSG_LENGTH_LIMIT = Integer.MAX_VALUE

    @JvmField
    val ANONYMOUS = "anonymous"

    @JvmField
    val MESSAGE_SENT_EVENT = "message_sent"

    val PROFILE_SETTING = 1
}