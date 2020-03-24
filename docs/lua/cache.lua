
--需要在nginx.conf 配置
-- lua_shared_dict my_cache 128m;
-- content_by_lua_file /xxxx/xxx/cache.lua
-- lua_package_path "/usr/local/opt/openresty/lualib/?.lua;;";
-- lua_package_cpath "/usr/local/opt/openresty/lualib/?.so;;";


--需要安装 在路径下/usr/local/opt/openresty/lualib
-- wget https://raw.githubusercontent.com/pintsized/lua-resty-http/master/lib/resty/http_headers.lua
--wget https://raw.githubusercontent.com/pintsized/lua-resty-http/master/lib/resty/http.lua


local uri = ngx.var.uri;
local hashValue = string.gsub(uri,'/r/','');
local cache_ngx = ngx.shared.my_cache

local hashKey = "short_hash_"..hashValue
local shortUrl = cache_ngx:get(hashKey)

if shortUrl == "" or shortUrl == nil then
    local http = require("resty.http")
    local httpc = http.new()

    local resp = httpc:request_uri("http://192.168.1.3:8080",{
        method = "GET",
        path = "/url/getByHash/"..hashValue
    })

    ngx.log(ngx.ERR,"============resp.body==========:", resp.body)

    local cjson = require("cjson")
    local resultJson = cjson.decode(resp.body)

    if resultJson.code == -1 then
        ngx.say('url not exist!')
        return
    end

    if resultJson.code == "Success" then
        shortUrl = resultJson.data
        cache_ngx:set(hashKey, shortUrl, 10 * 60)
        ngx.redirect(shortUrl, 302)
        return
    end

end

ngx.redirect(shortUrl, 302)
