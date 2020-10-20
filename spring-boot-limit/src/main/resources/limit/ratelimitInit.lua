-- curr_timestamp: redis current timestamp(Unit of second)
redis.replicate_commands()
local curr_timestamp = tonumber(redis.call('TIME')[1])

--[[
 last_second: token bucket last update timestamp(Unit of second)
 curr_permits: token bucket last permit amount
 max_burst: token bucket max amount
--]]
local result = 1
redis.call("HMSET", KEYS[1],
    "last_second", curr_timestamp,
    "curr_permits", ARGV[1],
    "max_burst", ARGV[2])
return result
