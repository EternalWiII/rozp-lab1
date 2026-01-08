CREATE TABLE IF NOT EXISTS travel_plans (
                                            id UUID PRIMARY KEY,
                                            title VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    budget DECIMAL(10, 2),
    currency VARCHAR(10),
    is_public BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             version INTEGER DEFAULT 1
                             );

CREATE TABLE IF NOT EXISTS locations (
                                         id UUID PRIMARY KEY,
                                         name VARCHAR(200) NOT NULL,
    address TEXT,
    latitude DECIMAL(10, 6),
    longitude DECIMAL(11, 6),
    visit_order INTEGER NOT NULL,
    arrival_date TIMESTAMP WITH TIME ZONE,
    departure_date TIMESTAMP WITH TIME ZONE,
                                 budget DECIMAL(10, 2),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 version INTEGER DEFAULT 1,
                                 travel_plan_id UUID REFERENCES travel_plans(id) ON DELETE CASCADE
    );