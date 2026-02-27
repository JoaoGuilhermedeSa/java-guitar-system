-- Guitar Specs (seed data)
INSERT INTO guitar_specs (id, number_of_strings, number_of_frets, scale_length_inches, body_wood, neck_wood, fretboard_wood, neck_pickup, bridge_pickup, bridge_type, finish_type, color)
VALUES (1, 6, 22, 25.5, 'ALDER', 'MAPLE', 'ROSEWOOD', 'HUMBUCKER', 'HUMBUCKER', 'FIXED', 'GLOSS_POLYURETHANE', 'Sunburst'),
       (2, 6, 21, 25.5, 'ASH', 'MAPLE', 'MAPLE_FRETBOARD', 'SINGLE_COIL', 'SINGLE_COIL', 'TREMOLO', 'SATIN_POLYURETHANE', 'Midnight Black'),
       (3, 6, 20, 25.4, 'SPRUCE', 'MAHOGANY', 'ROSEWOOD', 'PIEZO', 'NONE', 'ACOUSTIC_BRIDGE', 'NATURAL_OPEN_PORE', 'Natural'),
       (4, 4, 20, 34.0, 'ALDER', 'MAPLE', 'ROSEWOOD', 'HUMBUCKER', 'NONE', 'FIXED', 'GLOSS_POLYURETHANE', 'Tobacco Sunburst');

-- Guitar Models (seed data)
INSERT INTO guitar_models (id, name, description, output_style, default_spec_id, base_price, active)
VALUES (1, 'Thunderstrike Pro', 'Classic solid body electric with dual humbuckers', 'SOLID_BODY', 1, 1500.00, TRUE),
       (2, 'Midnight Standard', 'Versatile ash body with bright single-coil tones', 'SOLID_BODY', 2, 1200.00, TRUE),
       (3, 'Woodsman Acoustic', 'Full-bodied spruce top acoustic with natural finish', 'ACOUSTIC', 3, 900.00, TRUE),
       (4, 'Groove Master Bass', 'Punchy four-string bass for studio and stage', 'BASS', 4, 1100.00, TRUE);

-- Inventory (seed data)
INSERT INTO inventory (id, model_id, spec_id, quantity_available, reorder_threshold, last_updated)
VALUES (1, 1, 1, 5, 2, CURRENT_TIMESTAMP),
       (2, 2, 2, 8, 3, CURRENT_TIMESTAMP),
       (3, 3, 3, 1, 2, CURRENT_TIMESTAMP),
       (4, 4, 4, 3, 3, CURRENT_TIMESTAMP);

-- Reset identity counters to avoid conflicts with manual ID inserts
ALTER TABLE guitar_specs ALTER COLUMN id RESTART WITH 5;
ALTER TABLE guitar_models ALTER COLUMN id RESTART WITH 5;
ALTER TABLE inventory ALTER COLUMN id RESTART WITH 5;

-- Price Adjustments (seed data from previously hardcoded values)
INSERT INTO price_adjustments (id, item_key, description, adjustment_price)
VALUES (1, 'FLOYD_ROSE', 'Floyd Rose Locking Tremolo surcharge', 200.00),
       (2, 'BIGSBY', 'Bigsby Vibrato surcharge', 120.00),
       (3, 'ACTIVE_HUMBUCKER', 'Active Humbucker pickup surcharge (per unit)', 150.00),
       (4, 'KOA', 'Exotic Koa wood body surcharge', 300.00),
       (5, 'NITROCELLULOSE_LACQUER', 'Vintage-style Nitrocellulose Lacquer finish', 175.00),
       (6, 'EXTRA_STRING_FEE', 'Fee per additional string beyond 6', 100.00);

ALTER TABLE price_adjustments ALTER COLUMN id RESTART WITH 7;
