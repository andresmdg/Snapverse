import { CredentialsSignin } from "@auth/core/errors";
import Credentials from "@auth/core/providers/credentials";
import { defineConfig } from "auth-astro";

export default defineConfig({
  providers: [
    Credentials({
      credentials: {
        username: {},
        password: {
          type: "password",
        },
      },
      async authorize(cred) {
        const { API_URL } = import.meta.env;
        const user = { username: cred.username, password: cred.password };

        const response = await fetch(`${API_URL}/auth/login`, {
          method: "POST",
          headers: {
            "Content-type": "application/json",
          },
          body: JSON.stringify(user),
        }).then((data) => data.json());

        if (!cred) {
          throw new CredentialsSignin("Faltan credenciales");
        }

        if (!response.success) {
          throw new CredentialsSignin(response.message);
        }

        const { id, name, username, lastname } = response.user;

        return {
          id: id,
          name: name,
          lastname,
          username: username,
          tkn: response.token,
        };
      },
    }),
  ],
  pages: {
    signIn: "/signin",
  },
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        const { tkn, ...userData } = user;
        token.data = userData;
        token.token = tkn;
      }
      return token;
    },
    async session({ session, token }) {
      session.user.data = token.data;
      session.user.token = token.token;

      return session;
    },
  },
});
