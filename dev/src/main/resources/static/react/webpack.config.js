﻿module.exports = {
        context: __dirname,
        entry: "./index.js",
        output:
        {
            path: __dirname + "/dist",
            filename: "bundle.js"
    },
    mode: 'development',
    watch: true,
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets:['babel-preset-env','babel-preset-react']
                    }
                }
            }
        ]
    }
        
}