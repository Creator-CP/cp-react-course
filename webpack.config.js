const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

const pages = ['first', 'navigation', 'media', 'table', 'form','list_links'];

module.exports = {
  entry: pages.reduce((config, page) => {
    config[page] = `./src/${page}/${page}.js`;
    return config;
  }, {}),
  output: {
    filename: '[name].bundle.js',
    path: path.resolve(__dirname, 'dist'),
  },
  module: {
    rules: [
      {
        test: /\.scss$/,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader',
          'postcss-loader',
          'sass-loader',
        ],
      },
    ],
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: '[name].css',
    }),
    ...pages.map(
      (page) =>
        new HtmlWebpackPlugin({
          template: `./src/${page}/${page}.html`,
          filename: `${page}.html`,
          chunks: [page], // Подключает только свой JS и CSS
        })
    ),
  ],
  devServer: {
    static: {
      directory: path.join(__dirname, 'dist'),
    },
    port: 7747,
    hot: true,
  },
};
