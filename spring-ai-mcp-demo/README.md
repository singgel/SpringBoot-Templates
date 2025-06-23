# spring-ai-mcp-demo
SpringAI MCP demo 结合通义千问大模型

## 环境要求
- JDK 17+
- Maven 3.8.6+
- npm 10.9.2+
- python3.12.3+
- 需要去申请一个自己的千问大模型key
- SpringAI 1.0.0-M5 + SpringAI 1.0.0-M6

## 模块功能
- mcp-client模块：基于SpringAI 1.0.0-M5 版本，展示了如何使用FunctionCall的方式与MCP服务端对接
- call-mcp-server模块：基于SpringAI 1.0.0-M6 版本，展示了如何使用ToolCall的方式与MCP服务端对接
- mcp-server模块：简单的MCP服务端Demo（已经去除了数据库依赖，具体功能可以自己实现）

## call-mcp-server模块
call-mcp-server模块可以充当cursor或者Claude的角色，直接调用各种开源MCP服务例如百度地图服务  
修改call-mcp-server/src/main/resources/mcp-server.json中的内容即可  
详情参考：[掘金技术社区 10分钟带你集成百度地图MCP服务](https://juejin.cn/post/7485758756913266707)

## 帮助文档
- [掘金技术社区 SpringAI-MCP技术初探](https://juejin.cn/post/7483127098352877579)
