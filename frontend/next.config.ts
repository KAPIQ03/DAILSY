import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: 'export',
  basePath: '/~s51672/PAW/projekt/frontend/public',
  assetPrefix: '/~s51672/PAW/projekt/frontend/public/',
  images: {
    unoptimized: true,
  },
};

export default nextConfig;
