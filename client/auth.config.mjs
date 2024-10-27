import { CredentialsSignin } from "@auth/core/errors";
import Credentials from "@auth/core/providers/credentials";
import { defineConfig } from "auth-astro";

export default defineConfig({
  providers: [
    Credentials({
      id: "authsess",
      credentials: {
        username: {
          label: "Username",
        },
        password: {
          label: "Password",
          type: "password",
        },
      },
      async authorize(cred, request) {
        const userCred = {
          id: "1",
          name: "user",
          pass: "123",
        };
        console.log('Credenciales', cred)
        console.log('Request', request)
        const { username, password } = cred || {};
        if (!userCred) {
          throw new CredentialsSignin("Faltan credenciales");
        }

        // if (password !== userCred.pass) {
        //   throw new CredentialsSignin("Credenciales inválidas");
        // }
        return { id: userCred.id, name: userCred.name };
      },
    }),
  ],
  pages: {
    signIn: "/signin",
  },
});
