import { z } from "zod"

export const userListOutSchema = z.array(
  z.object({
    id: z.number(),
    firstName: z.string(),
    lastName: z.string(),
    shortDescription: z.string()
  })
)
export type UserListOut = z.infer<typeof userListOutSchema>

export const userDetailsOutSchema = z.object({
  id: z.number(),
  firstName: z.string(),
  lastName: z.string(),
  description: z.string(),
  website: z.string().url().nullable(),
  email: z.string().email(),
  birthday: z.coerce.date(),
  cvs: z.array(z.any())
})

export type UserDetailsOut = z.infer<typeof userDetailsOutSchema>
