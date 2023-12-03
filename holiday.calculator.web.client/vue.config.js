const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '^/': {
        target: 'http://192.168.0.230:8080',
        ws: false,
        changeOrigin: true
      }
    }
  }
})
