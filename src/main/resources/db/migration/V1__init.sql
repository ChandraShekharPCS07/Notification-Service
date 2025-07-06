-- user_preferences
CREATE TABLE user_preferences (
    user_id VARCHAR(50) PRIMARY KEY,
    sms_enabled BOOLEAN NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL
);

-- delivery_records
CREATE TABLE delivery_records (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    channel VARCHAR(20) NOT NULL,
    provider_message_id VARCHAR(100),
    status VARCHAR(20) NOT NULL,
    failure_reason TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL
);