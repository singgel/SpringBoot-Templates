-- curr_timestamp: redis current timestamp(Unit of second)
redis.replicate_commands()
local time = redis.call('time')
local curr_timestamp = tonumber(time[1])
local curr_microseconds = tonumber(time[2])
local require_permits = tonumber(ARGV[1])
local result = {}
-- result[1]: minimum time from next refresh(Unit of microsecond)
result[1] = (1000000 - curr_microseconds) / 1000

local ratelimit_info = redis.call("HMGET", KEYS[1], "last_second", "curr_permits", "max_burst")
local last_second = tonumber(ratelimit_info[1])
local curr_permits = tonumber(ratelimit_info[2])
local max_burst = tonumber(ratelimit_info[3])

-- If the last time has passed, update the token bucket
if (curr_timestamp > last_second) then
    curr_permits = max_burst
    redis.call("HMSET", KEYS[1], "last_second", curr_timestamp)
end

-- Update the last permit amount, base on the curr_permits change
if (curr_permits > require_permits) then
    redis.call("HMSET", KEYS[1], "curr_permits", curr_permits - require_permits)
    result[2] = require_permits
    return result
end

if (curr_permits > 0) then
    redis.call("HMSET", KEYS[1], "curr_permits", 0)
end
result[2] = curr_permits
return result
