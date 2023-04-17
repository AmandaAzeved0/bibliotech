const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: 'http://localhost:9091/api/v1',
    secure: false,
    logLevel: 'debug',
    pathRewrite: { '^/api': '' }

  }
];
module.exports = PROXY_CONFIG;
