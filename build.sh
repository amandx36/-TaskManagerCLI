#!/bin/bash
rm -rf out
mkdir -p out
javac -d out $(find src -name "*.java")
echo "Build complete!"
