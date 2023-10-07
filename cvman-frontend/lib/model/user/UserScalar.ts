import { z } from "zod"
import { userDetailsOutCvSchema } from "../cv/CVScalar"

export const userListOutSchema = z.array(
  z.object({
    id: z.number(),
    firstName: z.string(),
    lastName: z.string(),
    avatar: z.string().url().nullable(),
    shortDescription: z.string()
  })
)
export type UserListOut = z.infer<typeof userListOutSchema>

export const userDetailsOutSchema = z.object({
  id: z.number(),
  firstName: z.string(),
  lastName: z.string(),
  avatar: z.string().url().nullable(),
  description: z.string(),
  website: z.string().url().nullable(),
  email: z.string().email(),
  birthday: z.coerce.date(),
  cvs: z.array(userDetailsOutCvSchema)
})

export type UserDetailsOut = z.infer<typeof userDetailsOutSchema>
