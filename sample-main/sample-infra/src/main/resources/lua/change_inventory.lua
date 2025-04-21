-- Lua 脚本（用于 Redis Hash 中库存扣减）
-- KEYS[1]：库存的 Redis Hash key，例如 inventory:sku:10001
-- ARGV[1]: sellable 增量（如 -1）
-- ARGV[2]: withholding 增量（如 +1）
-- ARGV[3]: occupied 增量（如 0）

local key = KEYS[1]
local sellableDelta = tonumber(ARGV[1])
local withholdingDelta = tonumber(ARGV[2])
local occupiedDelta = tonumber(ARGV[3])

-- 获取当前库存字段值（如果为空返回0）
local sellable = tonumber(redis.call("HGET", key, "sellable") or "0")
local withholding = tonumber(redis.call("HGET", key, "withholding") or "0")
local occupied = tonumber(redis.call("HGET", key, "occupied") or "0")

-- 校验库存是否充足
if sellable + sellableDelta < 0 then
    return -1
end
if withholding + withholdingDelta < 0 then
    return -2
end
if occupied + occupiedDelta < 0 then
    return -3
end

-- 原子扣减
redis.call("HINCRBY", key, "sellable", sellableDelta)
redis.call("HINCRBY", key, "withholding", withholdingDelta)
redis.call("HINCRBY", key, "occupied", occupiedDelta)

-- 返回操作后的 sellable 库存值
return sellable + sellableDelta