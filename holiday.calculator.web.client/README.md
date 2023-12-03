# holiday.calculator.web.client

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

launch.json:
{
    "version": "0.2.0",
    "configurations": [
        {
            "name": "Launch wildfly",
            "type": "firefox",
            "request": "launch",
            "reAttach": true,
            "url": "http://192.168.0.230:8080/",
            "webRoot": "${workspaceFolder}",
            "pathMappings": [
                {
                    "url": "webpack://holiday.calculator.web.client/src",
                    "path": "${workspaceFolder}/src"
                },
                {
                    "url": "webpack://holiday.calculator.web.client/src/router",
                    "path": "${workspaceFolder}/src/store"
                }
            ]
        },
        {
            "name": "Launch local",
            "type": "firefox",
            "request": "launch",
            "reAttach": true,
            "url": "http://localhost:8081/",
            "webRoot": "${workspaceFolder}",
            "pathMappings": [
                {
                    "url": "webpack://holiday.calculator.web.client/src",
                    "path": "${workspaceFolder}/src"
                },
                {
                    "url": "webpack://holiday.calculator.web.client/src/router",
                    "path": "${workspaceFolder}/src/store"
                }
            ]
        }
    ]
}
