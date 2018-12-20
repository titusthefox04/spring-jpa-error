-- Updates the hibernate sequence so that automatically created entities start from 1000
-- update hibernate_sequence set next_val = (next_val + 1000) where next_val = 1;

alter sequence hibernate_sequence restart with 1000;
